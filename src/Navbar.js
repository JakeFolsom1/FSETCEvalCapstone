import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Evaluations from "./Evaluations.js";
import Assign from "./Assign.js";
import ManageUsers from "./ManageUsers.js";
import ManageSemesters from "./ManageSemesters.js";
import logo from "./assets/fsetcLogo.png";
import "./Navbar.css";

function NavItem(props) {
  return (
    <li>
      <Route
        path={props.to}
        exact={true}
        children={({ match }) => (
          <div className={"nav-item" + (match ? " active" : "")}>
            <Link
              class={"nav-link asu-link" + (match ? "-active" : "")}
              to={props.to}
            >
              {props.label}
            </Link>
          </div>
        )}
      />
    </li>
  );
}

function Navbar(props) {
  return (
    <Router>
      <nav class="navbar navbar-expand-lg navbar-light bg-white align-items-end">
        <Link class="navbar-brand tc-img-link" to="/">
          <img
            class="img-fluid"
            src={logo}
            alt="Fulton Schools of Engineer Tutoring Center Logo"
          />
        </Link>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon" />
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav nav-fill">
            <NavItem label="Evaluations" to="/" />

            <NavItem label="Assign" to="/assign/" />

            <NavItem label="Manage Users" to="/manage_users/" />

            <NavItem label="Manage Semesters" to="/manage_semesters/" />
          </ul>
          <span class="navbar-text ml-auto" id="welcome-message">
            Welcome, {props.activeUser}
          </span>
        </div>
      </nav>
      <Route path="/" exact component={Evaluations} />
      <Route path="/assign/" component={Assign} />
      <Route path="/manage_users/" component={ManageUsers} />
      <Route path="/manage_semesters/" component={ManageSemesters} />
    </Router>
  );
}

export default Navbar;
