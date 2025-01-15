import axios from "axios";
const customErrors = [400, 404, 409, 429, 500];
const axiosApiClient = axios.create({
  baseURL: "http://localhost:8080/tenpo/api/v1/",
});

export async function getTransactionByAxios(transactionId) {
  console.log("AxiosService - Get Transaction: " + transactionId);
  return axiosApiClient
    .get("/transaction", {
      params: {
        transactionId: transactionId,
      },
    })
    .then(function (response) {
      console.log("Response:");
      console.log(response.data);
      return response.data;
    })
    .catch(function (error) {
      if (customErrors.includes(error.status)) {
        return error.response.data;
      }
    });
}

export async function getTransactionsByTenpistaByAxios(userName) {
  console.log("AxiosService - Get Transaction By User Name: " + userName);
  return axiosApiClient
    .get("/transaction/tenpista", {
      params: {
        userName: userName,
      },
    })
    .then(function (response) {
      console.log("Response:");
      console.log(response.data);
      return response.data;
    })
    .catch(function (error) {
      if (customErrors.includes(error.status)) {
        return error.response.data;
      }
    });
}
export async function deleteTransactionByAxios(transactionId) {
  console.log("AxiosService - Delete Transaction: " + transactionId);
  return axiosApiClient
    .delete("/transaction", {
      params: {
        transactionId: transactionId,
      },
    })
    .then(function (response) {
      console.log("Response:");
      console.log(response.data);
      return response.data;
    })
    .catch(function (error) {
      if (customErrors.includes(error.status)) {
        return error.response.data;
      }
    });
}

export async function createTransactionByAxios(fullFormRequest) {
  console.log("AxiosService - Create Transaction: " + fullFormRequest);

  return axiosApiClient
    .post("http://localhost:8080/tenpo/api/v1/transaction", fullFormRequest, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then(function (response) {
      console.log("Response:");
      console.log(response.data);
      return response.data;
    })
    .catch(function (error) {
      if (customErrors.includes(error.status)) {
        return error.response.data;
      }
    });
}

export async function updateTransactionByAxios(fullFormRequest) {
  console.log("AxiosService - Update Transaction: " + fullFormRequest);

  return axiosApiClient
    .put("http://localhost:8080/tenpo/api/v1/transaction", fullFormRequest, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then(function (response) {
      console.log("Response:");
      console.log(response.data);
      return response.data;
    })
    .catch(function (error) {
      if (customErrors.includes(error.status)) {
        return error.response.data;
      }
    });
}
