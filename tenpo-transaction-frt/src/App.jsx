import { useState } from "react";
import "./App.css";
import Header from "./components/Header/Header";
import Landing from "./components/landing/Landing/Landing";
import CrudMain from "./components/crud/CrudMain/CrudMain";
function App() {
  const [step, setStep] = useState(1);
  const [otro, setOtro] = useState("Hola");

  return (
    <>
      <Header />
      {step == 1 && <Landing setStep={setStep} />}
      {step == 2 && <CrudMain setStep={setStep} />}
    </>
  );
}

export default App;
