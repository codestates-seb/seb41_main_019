import { BrowserRouter, Routes, Route } from "react-router-dom";
import MyPage from "./page/MyPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={"/"} element={<MyPage />} />
        {/* <Route path={"/"} element={} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;