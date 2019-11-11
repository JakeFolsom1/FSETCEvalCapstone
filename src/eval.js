function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function openTab(e, tabName){
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabContent");
    for (i = 0; i < tabcontent.length; i++)
    {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tabLink");
    for (i = 0; i < tablinks.length; i++)
    {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    tablinks = document.getElementsByClassName("tabLink");
    for (i = 0; i < tablinks.length; i++)
    {

    }
    document.getElementById(tabName).style.display = "block";
    e.currentTarget.className += "active";
}

function generateTable(document){
    //Foreach element in API call return, create a new <tr> element as well as <th> for every <tr>
}
