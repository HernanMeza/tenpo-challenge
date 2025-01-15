import { useEffect, useState } from "react";

import FullForm from "../FullForm/FullForm";
import "./InfoBox.css";
import {
  createTransactionByAxios,
  deleteTransactionByAxios,
  getTransactionByAxios,
  getTransactionsByTenpistaByAxios,
  updateTransactionByAxios,
} from "../../../services/AxiosService";

export default function InfoBox(props) {
  const [formRequest, setFormRequest] = useState({});
  const [objectToUpdate, setObjectToUpdate] = useState(null);

  /*State UseEffect */
  /*Validador primera ejecucion */
  const [isFormSubmitted, setIsFormSubmitted] = useState(false);
  /* Triggers de los useEffect */
  const [isGetRequested, setIsGetRequested] = useState(false);
  const [isGetTenpistaRequested, setIsGetTenpistaRequested] = useState(false);
  const [isPostRequested, setIsPostRequested] = useState(false);
  const [isUpdateRequested, setIsUpdateRequested] = useState(false);
  const [isDeleteRequested, setIsDeleteRequested] = useState(false);

  function clearState() {
    setIsFormSubmitted(false);
    setFormRequest({});
    setIsGetRequested(false);
    setIsGetTenpistaRequested(false);
    setIsPostRequested(false);
    setIsDeleteRequested(false);
    setIsUpdateRequested(false);
  }

  function handleClickTabToUpdate() {
    console.log("handleClickTabToUpdate");
    console.log(props.apiResponse.body.transactions?.[0]);
    setObjectToUpdate(props.apiResponse);
    props.setFormData(props.apiResponse.body.transactions?.[0]);

    props.setClickedTab("3");
    props.setIsFullFormTab(true);
    props.setIsEditing(true);
    props.setApiResponse(null);
    console.log(objectToUpdate);
  }

  function handleSubmitFormulario(event) {
    event.preventDefault();

    console.log("handleSubmit");
    setIsFormSubmitted(true);

    const formEl = event.currentTarget;
    const refFormulario = new FormData(formEl);

    let request = {
      transactionId: "",
      userName: "",
      commerce: "",
      totalAmount: "",
      transactionDate: "",
    };

    switch (props.clickedTab) {
      case "0":
        console.log("handleSubmit - GET");

        request.transactionId = refFormulario.get("transactionId");
        setIsGetRequested(true);
        break;

      case "1":
        console.log("handleSubmit - GET Tenpista");

        request.userName = refFormulario.get("userName");
        setIsGetTenpistaRequested(true);
        break;

      case "2" /* "Crear", */:
        console.log("handleSubmit - PUT");

        request.userName = refFormulario.get("userName");
        request.commerce = refFormulario.get("commerce");
        request.totalAmount = refFormulario.get("totalAmount");
        request.transactionDate = new Date(
          refFormulario.get("transactionDate")
        ).toISOString();

        setIsPostRequested(true);
        break;

      case "3" /* "Actualizar", */:
        console.log("handleSubmit - PUT");

        request.transactionId = refFormulario.get("transactionId");
        request.userName = refFormulario.get("userName");
        request.commerce = refFormulario.get("commerce");
        request.totalAmount = refFormulario.get("totalAmount");
        request.transactionDate = new Date(
          refFormulario.get("transactionDate")
        ).toISOString();

        setIsUpdateRequested(true);

        break;

      case "4" /* "Eliminar", */:
        console.log("handleSubmit - Delete");
        request.transactionId = refFormulario.get("transactionId");
        setIsDeleteRequested(true);
        break;

      default:
        console.log("Error al seleccionar la opción.");
        break;
    }
    setFormRequest(request);
  }

  /**USE EFFECT PARA OBTENCION POR ID DE TRANSACCION */
  useEffect(() => {
    async function getTransaction() {
      if (isFormSubmitted) {
        try {
          const response = await getTransactionByAxios(
            formRequest.transactionId
          );
          props.setApiResponse(response);
        } catch (error) {
          console.error("Error al obtener la transacción:", error);
          setIsFormSubmitted(false);
        } finally {
          clearState();
        }
      }
    }
    getTransaction();
  }, [isGetRequested]);

  /**USE EFFECT PARA OBTENCION POR TENPISTA */
  useEffect(() => {
    async function getTransactionTenpista() {
      if (isFormSubmitted) {
        try {
          console.log("getTransactionTenpista useEffect");
          const response = await getTransactionsByTenpistaByAxios(
            formRequest.userName
          );
          props.setApiResponse(response);
        } catch (error) {
          console.error("Error al obtener la transacción:", error);
          setIsFormSubmitted(false);
        } finally {
          clearState();
        }
      }
    }
    getTransactionTenpista();
  }, [isGetTenpistaRequested]);

  /**USE EFFECT PARA ELIMINACION POR ID DE TRANSACCION */
  useEffect(() => {
    async function deleteTransaction() {
      if (isFormSubmitted) {
        try {
          const response = await deleteTransactionByAxios(
            formRequest.transactionId
          );
          props.setApiResponse(response);
        } catch (error) {
          console.error("Error al obtener la transacción:", error);
        } finally {
          clearState();
        }
      }
    }
    deleteTransaction();
  }, [isDeleteRequested]);

  /**USE EFFECT PARA CREACION DE TRANSACCION */
  useEffect(() => {
    async function postTransaction() {
      if (isFormSubmitted) {
        try {
          const response = await createTransactionByAxios(formRequest);
          props.setApiResponse(response);
        } catch (error) {
          console.error("Error al obtener la transacción:", error);
        } finally {
          clearState();
        }
      }
    }
    postTransaction();
  }, [isPostRequested]);

  /**USE EFFECT PARA ACTUALIZACION DE TRANSACCION */
  useEffect(() => {
    async function updateTransaction() {
      if (isFormSubmitted) {
        try {
          const response = await updateTransactionByAxios(formRequest);
          props.setApiResponse(response);
        } catch (error) {
          console.error("Error al obtener la transacción:", error);
        } finally {
          clearState();
        }
      }
    }
    updateTransaction();
  }, [isUpdateRequested]);

  return (
    <div id="info-box" className="info-box-frame">
      <FullForm
        clickedTab={props.clickedTab}
        isFullFormTab={props.isFullFormTab}
        isEditing={props.isEditing}
        apiResponse={props.apiResponse}
        handleSubmit={handleSubmitFormulario}
        formData={props.formData}
        setFormData={props.setFormData}
        handleClickTabToUpdate={handleClickTabToUpdate}
      />
    </div>
  );
}
