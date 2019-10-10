import React from "react";
import { Route } from "react-router-dom";
import Sidebar from "./SideBar.js";
import FinishedEvals from "./FinishedEvals.js";
import EvalProgress from "./EvalProgress.js";
import EditEvals from "./EditEvals.js";

function Evaluations(props) {
  const sideItems = [
    {
      title: "View Finished Evaluations",
      component: FinishedEvals,
      to: "/evals/"
    },
    {
      title: "Check Evaluation Progress",
      component: EvalProgress,
      to: "/evals/eval_progress"
    },
    { title: "Edit Evaluations", component: EditEvals, to: "/evals/edit_evals" }
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

export default Evaluations;
