import "./Header.css";

export default function Header() {
  return (
    <div id="header" className="header-frame">
      <a href="https://www.tenpo.cl/">
        <img
          loading="lazy"
          src="../../assets/img/logo_tenpo.svg"
          alt="logo Tenpo"
          className="ner-logo-1"
        />
      </a>
    </div>
  );
}
