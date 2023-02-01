import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { createGlobalStyle } from "styled-components";

const StyledGlobal = createGlobalStyle` 
  * {
    box-sizing: border-box;
  }

  body {
    margin: 0px 0px 0px 0px;
    padding: 0px 0px 0px 0px;
    font-family: "pretendard";
  }

  a {
    text-decoration: none;
  }
`;
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <>
    <StyledGlobal />
    <App />
  </>
);
