import { useState } from "react";
import "./CustomTable.css";

export default function CustomTable(props) {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10); // Cambia este valor para ajustar la cantidad de elementos por página.
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  let isEmptyTable = false;

  let currentItems;
  if (props.apiResponse.body) {
    currentItems = props.apiResponse.body.transactions.slice(
      indexOfFirstItem,
      indexOfLastItem
    );
  } else {
    console.log("Nada que mostrar");
    isEmptyTable = true;
  }

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div id="custom-table" className="custom-table-frame">
      {isEmptyTable ? (
        <>
          <table border={1}>
            <thead>
              <tr>
                <th>ID Transacción</th>
                <th>Monto Total</th>
                <th>Comercio</th>
                <th>Usuario</th>
                <th>Fecha de Transacción</th>
              </tr>
            </thead>{" "}
          </table>
        </>
      ) : (
        <>
          <table border={1}>
            <thead>
              <tr>
                <th>ID Transacción</th>
                <th>Monto Total</th>
                <th>Comercio</th>
                <th>Usuario</th>
                <th>Fecha de Transacción</th>
              </tr>
            </thead>
            <tbody>
              {currentItems.map((transaction) => (
                <tr key={transaction.transactionId}>
                  <td>{transaction.transactionId}</td>
                  <td>{transaction.totalAmount}</td>
                  <td>{transaction.commerce}</td>
                  <td>{transaction.userName}</td>
                  <td>
                    {new Date(transaction.transactionDate).toLocaleString()}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* Paginación */}
          <div className="page-btn-block">
            {Array.from(
              {
                length: Math.ceil(
                  props.apiResponse.body.transactions.length / itemsPerPage
                ),
              },
              (_, index) => (
                <button
                  key={index + 1}
                  onClick={() => paginate(index + 1)}
                  className={`page-btn ${
                    currentPage === index + 1 ? "active" : ""
                  }`}
                >
                  {index + 1}
                </button>
              )
            )}
          </div>
        </>
      )}
    </div>
  );
}
