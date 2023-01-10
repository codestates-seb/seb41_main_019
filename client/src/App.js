import { BrowserRouter, Routes, Route } from "react-router-dom";
import Sidebar from "./components/public/Sidebar";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={"/"} element={<Sidebar />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;