import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Sidebar from "./SideBar.js";

function FinishedEvals(props) {
  return <h2>Finished Evals</h2>;
}

function EvalProgress(props) {
  return <h2>Eval Progress</h2>;
}

function EditEvals(props) {
  return <h2>Edit Evals</h2>;
}

function Evaluations(props) {
  const sideItems = [
    { title: "View Finished Evaluations", component: FinishedEvals, to: "/" },
    {
      title: "Check Evaluation Progress",
      component: EvalProgress,
      to: "/eval_progress/"
    },
    { title: "Edit Evaluations", component: EditEvals, to: "/edit_evals/" }
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

export default Evaluations;
