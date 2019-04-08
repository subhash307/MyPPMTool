import React, { Component } from "react";
import ProjectItem from "./Projects/ProjectItem";

class Dashboard extends Component {
  render() {
    return (
      <div>
        <h1>Welcome to dashboard</h1>
        <ProjectItem />
      </div>
    );
  }
}

export default Dashboard;
