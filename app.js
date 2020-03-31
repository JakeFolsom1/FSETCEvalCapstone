const path = require('path');
const fetch = require('node-fetch');
const session = require('express-session');
const CASAuthentication = require('cas-authentication');
const express = require('express');
const app = express();
const port = 3000;

app.use(session({
    secret: 'super secret key',
    resave: false,
    saveUninitialized: true
}));

const cas = new CASAuthentication({
    cas_url: 'https://sod819.fulton.asu.edu/cas',
    service_url: '/',
    cas_version: '3.0',
    renew: false,
    is_dev_mode: false,
    dev_mode_user: '',
    dev_mode_info: {},
    session_name: 'cas_user',
    session_info: 'cas_userinfo',
    destroy_session: false,
    single_logout: false
});

app.use('/public', express.static(path.join(__dirname, 'public')));
app.get('/', cas.bounce, function (req, res) {

    // get asurite from CAS
    const asurite = req.session[cas.session_name];

    fetch(`https://sod819.fulton.asu.edu/eval-app/staff/${asurite}`, {
        headers: { 'Accept': 'application/json' }
    })
        .then(res => res.json()).then(staff => {
            if (!staff.role) {
                res.send("Unauthorized");
            } else if (staff.role === 'ADMIN') {
                app.use('/admin', express.static(path.join(__dirname, 'src/admin_js/')));
                res.sendFile('./src/admin.html', { root: __dirname });
            } else if (staff.role === 'LEAD') {
                app.use('/lead', express.static(path.join(__dirname, 'src/lead_js/')));
                res.sendFile('./src/lead.html', { root: __dirname });
            } else {
                console.log("Must be a tutor");
                res.send("<h2>Tutor view</h2>");
            }

        }).catch(err => {
            console.error(err);
            res.send("Unauthorized");
        });

});


app.listen(port, () => console.log(`App listening on port ${port}`));