import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";
import Chat from "./components/public/Chat/Chat";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>{/* <Route path={"/"} element={<App />} /> */}</Routes>
      <Chat />
    </BrowserRouter>
  );
}

export default App;
