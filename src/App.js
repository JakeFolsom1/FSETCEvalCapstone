import React from "react";
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
        <div className="row">
          <div className="col">
            <Navbar activeUser="Jessica" />
          </div>
        </div>
        <Route path="/" exact component={Evaluations} />
        <Route path="/assign/" exact component={Assign} />
        <Route path="/manage_users/" exact component={ManageUsers} />
        <Route path="/manage_semesters/" exact component={ManageSemesters} />
      </Router>
    </div>
  );
}

export default App;
