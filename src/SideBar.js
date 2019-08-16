import React from "react";
import { NavLink } from "react-router-dom";

function SideItem(props) {
  return (
    <NavLink
      exact
      className="nav-link text-secondary"
      activeClassName="active"
      to={props.to}
    >
      {props.label}
    </NavLink>
  );
}

function SideBar(props) {
  return (
    <ul className="nav nav-pills nav-fill bg-dark flex-column h-100">
      {props.sideItems.map(sideItem => {
        return (
          <li className="nav-item" key={sideItem.title}>
            <SideItem label={sideItem.title} to={sideItem.to} />
          </li>
        );
      })}
    </ul>
  );
}

export default SideBar;
