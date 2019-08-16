import React from "react";
import { Redirect } from "react-router-dom";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "./App.css";
import Navbar from "./Navbar.js";
import Evaluations from "./Evaluations.js";
import Assign from "./Assign.js";
import ManageUsers from "./ManageUsers.js";
import ManageSemesters from "./ManageSemesters.js";

function App() {
  return (
    <div className="app container-fluid">
      <Router>
        <Redirect to="/evals/" />
        <div className="row">
          <div className="col">
            <Navbar activeUser="Jessica" />
          </div>
        </div>
        <Route path="/evals/" component={Evaluations} />
        <Route path="/assign" component={Assign} />
        <Route path="/manage_users/" component={ManageUsers} />
        <Route path="/manage_semesters" component={ManageSemesters} />
      </Router>
    </div>
  );
}

export default App;
