import React, { Component } from "react";
import ProjectItem from "./Projects/ProjectItem";

class Dashboard extends Component {
  render() {
    return (
      <div>
        <h1 className="alert alert-warning">Welcome to dashboard</h1>
        <ProjectItem />
      </div>
    );
  }
}

export default Dashboard;
