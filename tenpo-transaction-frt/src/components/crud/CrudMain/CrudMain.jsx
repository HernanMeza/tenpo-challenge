import { useState } from "react";
import "./CrudMain.css";
import OptionsTab from "../OptionsTab/OptionsTab";
import InfoBox from "../InfoBox/InfoBox";

export default function CrudMain(props) {
  const [clickedTab, setClickedTab] = useState("1");
  const [isFullFormTab, setIsFullFormTab] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  /*State general del formulario*/
  const [formData, setFormData] = useState(null);
  const [apiResponse, setApiResponse] = useState(null);

  function handleClickTab(id, isFullFormTab, isEditing) {
    console.log(
      "Clickeado: " +
        id +
        " Con FullForm en: " +
        isFullFormTab +
        " IsEditing: " +
        isEditing
    );
    setClickedTab(id);
    setIsFullFormTab(isFullFormTab);
    setIsEditing(isEditing);
    setApiResponse(null);
    setFormData(null);
  }

  return (
    <div id="crud-main" className="crud-main-frame">
      <h1 className="header-h1 center tenpo-green">Manejo de transacciones!</h1>
      <div className="main-box">
        <OptionsTab
          clickedTab={clickedTab}
          setClickedTab={setClickedTab}
          isFullFormTab={isFullFormTab}
          setIsFullFormTab={setIsFullFormTab}
          isEditing={isEditing}
          setIsEditing={setIsEditing}
          setApiResponse={setApiResponse}
          handleClickTab={handleClickTab}
        />
        <InfoBox
          clickedTab={clickedTab}
          setClickedTab={setClickedTab}
          isFullFormTab={isFullFormTab}
          setIsFullFormTab={setIsFullFormTab}
          isEditing={isEditing}
          setIsEditing={setIsEditing}
          apiResponse={apiResponse}
          setApiResponse={setApiResponse}
          formData={formData}
          setFormData={setFormData}
        />
      </div>
    </div>
  );
}
