import "./OptionsTab.css";
import { optionTabsArray } from "../../../services/TabServices";

export default function OptionsTab(props) {
  return (
    <div className="option-tabs-frame" role="tablist">
      {optionTabsArray.map((tab) => {
        return (
          <button
            key={tab.id}
            className={
              props.clickedTab == tab.id
                ? "option-tabs clicked-tab"
                : "option-tabs"
            }
            onClick={() =>
              props.handleClickTab(tab.id, tab.isFullFormTab, tab.isEditing)
            }
          >
            {tab.optionName}
          </button>
        );
      })}
    </div>
  );
}
