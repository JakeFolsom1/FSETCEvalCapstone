import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Sidebar from "./SideBar.js";

function CurrentUsers(props) {
  return <h2>Current Users</h2>;
}

function AddUsers(props) {
  return <h2>Add Users</h2>;
}

function ImportUsers(props) {
  return <h2>Import Users</h2>;
}

function ManageUsers(props) {
  const sideItems = [
    {
      title: "View Current Users",
      component: CurrentUsers,
      to: "/manage_users/"
    },
    {
      title: "Add New Users",
      component: AddUsers,
      to: "/manage_users/add_users"
    },
    {
      title: "Import Users",
      component: ImportUsers,
      to: "/manage_users/import_users/"
    }
  ];
  return (
    <Router>
      <div className="row h-100">
        <div className="col-2">
          <Sidebar sideItems={sideItems} />
        </div>
        <div className="col-10">
          {sideItems.map(sideItem => {
            return (
              <Route
                key={sideItem.title}
                path={sideItem.to}
                exact
                component={sideItem.component}
              />
            );
          })}
        </div>
      </div>
    </Router>
  );
}

export default ManageUsers;
