import "./Landing.css";

export default function Landing(props) {
  return (
    <div id="landing" className="landing-frame">
      <div className="content">
        <div className="landing-text-block">
          <div className="general-text">
            <h1 className="header-h1">¡Bienvenidos a Tenpo Transaction!</h1>
            <h2 className="header-h2">
              Exploremos esta solución para el manejo de transacciones propuesto
              como un...{" "}
              <span className="tenpo-green">
                <strong> challenge que resuelve</strong>
              </span>
            </h2>
            <button className="tenpo-btn" onClick={() => props.setStep(2)}>
              Explorar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
