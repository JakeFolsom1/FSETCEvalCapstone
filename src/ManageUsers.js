import React from "react";
import { Route } from "react-router-dom";
import Sidebar from "./SideBar.js";
import CurrentUsers from "./CurrentUsers.js";
import AddUsers from "./AddUsers.js";
import ImportUsers from "./ImportUsers.js";

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
      to: "/manage_users/import_users"
    }
  ];
  return (
    <div className="row h-100">
      <div className="d-none d-lg-block  col-lg-2">
        <Sidebar sideItems={sideItems} />
      </div>
      <div className="col-lg-10 col-md-12">
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
  );
}

export default ManageUsers;
