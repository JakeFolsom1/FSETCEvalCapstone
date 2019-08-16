import React from "react";
import { Route, Link } from "react-router-dom";

function SideItem(props) {
  return (
    <Route
      path={props.to}
      exact={true}
      children={({ match }) => (
        <Link
          className={match ? "nav-link active" : "nav-link text-seconday"}
          to={props.to}
        >
          {props.label}
        </Link>
      )}
    />
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
