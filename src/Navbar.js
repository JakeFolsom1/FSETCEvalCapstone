import React from "react";
import { NavLink } from "react-router-dom";
import { Route, Link } from "react-router-dom";
import logo from "./assets/fsetcLogo.png";
import "./Navbar.css";

function NavItem(props) {
  return (
    <li>
      <div className="nav-item">
        <NavLink
          to={props.to}
          className="nav-link asu-link"
          activeClassName="active asu-link-active"
        >
          {props.label}
        </NavLink>
      </div>
      {props.children && (
        <Route
          path={props.to}
          component={() => (
            <ul>
              <div class="nav-item d-md-block d-lg-none">
                {props.children.map(child => (
                  <li>
                    <NavLink
                      exact
                      to={child.to}
                      className="nav-link asu-link"
                      activeClassName="active asu-link-active"
                    >
                      {child.title}
                    </NavLink>
                  </li>
                ))}
              </div>
            </ul>
          )}
        />
      )}
    </li>
  );
}

function Navbar(props) {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white align-items-end">
      <Link className="navbar-brand tc-img-link" to="/evals/">
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
          <NavItem
            label="Evaluations"
            to="/evals/"
            children={[
              {
                title: "View Finished Evaluations",
                to: "/evals/"
              },
              {
                title: "Check Evaluation Progress",
                to: "/evals/eval_progress"
              },
              {
                title: "Edit Evaluations",
                to: "/evals/edit_evals"
              }
            ]}
          />

          <NavItem label="Assign" to="/assign" />

          <NavItem
            label="Manage Users"
            to="/manage_users/"
            children={[
              {
                title: "View Current Users",
                to: "/manage_users/"
              },
              {
                title: "Add New Users",
                to: "/manage_users/add_users"
              },
              {
                title: "Import Users",
                to: "/manage_users/import_users"
              }
            ]}
          />

          <NavItem label="Manage Semesters" to="/manage_semesters" />
        </ul>
        <span className="navbar-text ml-auto" id="welcome-message">
          Welcome, {props.activeUser}
        </span>
      </div>
    </nav>
  );
}

export default Navbar;
