import React from "react";
import { Pagination } from "react-bootstrap";

const PaginationPage = ({ pageCurrent = 1, totalPages, setPageCurrent }) => {
  function handleClick(event) {
    setPageCurrent(event.target.outerText - 1);
  }

  let active = pageCurrent === 0 ? 1 : pageCurrent + 1;
  let items = [];
  for (let number = 1; number <= totalPages; number++) {
    items.push(
      <Pagination.Item
        onClick={handleClick}
        key={number}
        active={number === active}
      >
        {number}
      </Pagination.Item>
    );
  }

  return (
    <Pagination className="pagination-company" size="sm">
      {items}
    </Pagination>
  );
};

export default PaginationPage;
