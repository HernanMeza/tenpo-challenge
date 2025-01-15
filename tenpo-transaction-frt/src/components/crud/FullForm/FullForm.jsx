import { optionTabsArray } from "../../../services/TabServices";
import CustomTable from "../CustomTable/CustomTable";
import "./FullForm.css";

export default function FullForm(props) {
  // Función para manejar los cambios en los inputs
  const handleChangeForm = (event) => {
    const { name, value } = event.target;
    props.setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  function dateFormatValidation(transactionDate) {
    let rtn;
    if (transactionDate) {
      if (transactionDate.length === 20) {
        rtn = transactionDate.slice(0, -1);
      } else {
        rtn = transactionDate;
      }
    } else {
      return transactionDate;
    }
    return rtn;
  }

  return (
    <div id="full-form" className="full-form-frame">
      <form onSubmit={props.handleSubmit} className="main-form">
        {optionTabsArray[props.clickedTab].showTransactionId && (
          <>
            <label htmlFor="form-transaction-id">
              Numero de Transacción{" "}
              <sup className="ml-1 font-normal opacity-75">*</sup>
            </label>
            <input
              type="number"
              id="form-transaction-id"
              name="transactionId"
              onChange={handleChangeForm}
              value={props.formData?.transactionId || ""}
              placeholder="3"
              required
              min={0}
              autoFocus
            />
          </>
        )}

        {optionTabsArray[props.clickedTab].showSearchByTenpista && (
          <>
            <label htmlFor="form-userName">
              Nombre Tenpista{" "}
              <sup className="ml-1 font-normal opacity-75">*</sup>
            </label>
            <input
              id="form-userName"
              type="text"
              name="userName"
              onChange={handleChangeForm}
              value={props.formData?.userName || ""}
              placeholder="Alison"
              minLength={3}
              maxLength={30}
              required
            />
          </>
        )}
        {props.isFullFormTab && (
          <>
            <label htmlFor="form-commerce">
              Comercio <sup className="ml-1 font-normal opacity-75">*</sup>
            </label>
            <input
              id="form-commerce"
              type="text"
              name="commerce"
              onChange={handleChangeForm}
              value={props.formData?.commerce || ""}
              placeholder="Starbucks"
              minLength={2}
              maxLength={100}
              required
            />

            <label htmlFor="form-totalAmount">
              Monto Total <sup className="ml-1 font-normal opacity-75">*</sup>
            </label>
            <input
              type="number"
              id="form-totalAmount"
              name="totalAmount"
              onChange={handleChangeForm}
              value={props.formData?.totalAmount || ""}
              placeholder="23.400"
              required
              min={0}
            />

            <label htmlFor="form-transactionDate">
              Fecha de la Transacción{" "}
              <sup className="ml-1 font-normal opacity-75">*</sup>
            </label>
            <input
              id="form-transactionDate"
              type="datetime-local"
              step="1"
              name="transactionDate"
              onChange={handleChangeForm}
              value={
                props.formData?.totalAmount
                  ? dateFormatValidation(props.formData?.transactionDate)
                  : ""
              }
              required
            />
          </>
        )}
        <div className="form-btns">
          <button type="submit" className="tenpo-btn">
            {optionTabsArray[props.clickedTab].textMainButton}
          </button>

          {props.apiResponse != null &&
            !props.isEditing &&
            optionTabsArray[props.clickedTab].canShowUpdate && (
              <button
                onClick={() => props.handleClickTabToUpdate(4, true, true)}
                className="tenpo-btn-white"
              >
                ¿Actualizamos?
              </button>
            )}
        </div>
        {props.apiResponse != null &&
          optionTabsArray[props.clickedTab].canShowJson && (
            <pre>{JSON.stringify(props.apiResponse, null, 2)}</pre>
          )}
      </form>

      <div>
        {props.apiResponse &&
          optionTabsArray[props.clickedTab].canShowTable &&
          props.apiResponse && <CustomTable apiResponse={props.apiResponse} />}
      </div>
    </div>
  );
}
