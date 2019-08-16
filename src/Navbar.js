import React from "react";
import { Route, Link } from "react-router-dom";
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
              className={"nav-link asu-link" + (match ? "-active" : "")}
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
    <nav className="navbar navbar-expand-lg navbar-light bg-white align-items-end">
      <Link className="navbar-brand tc-img-link" to="/">
        <img
          className="img-fluid"
          src={logo}
          alt="Fulton Schools of Engineer Tutoring Center Logo"
        />
      </Link>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNavDropdown"
        aria-controls="navbarNavDropdown"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon" />
      </button>
      <div className="collapse navbar-collapse" id="navbarNavDropdown">
        <ul className="navbar-nav nav-fill">
          <NavItem label="Evaluations" to="/" />

          <NavItem label="Assign" to="/assign/" />

          <NavItem label="Manage Users" to="/manage_users/" />

          <NavItem label="Manage Semesters" to="/manage_semesters/" />
        </ul>
        <span className="navbar-text ml-auto" id="welcome-message">
          Welcome, {props.activeUser}
        </span>
      </div>
    </nav>
  );
}

export default Navbar;
