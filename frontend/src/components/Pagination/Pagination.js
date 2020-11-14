import React from "react";
import { Pagination } from "react-bootstrap";

const PaginationPage = ({ pageCurrent, totalPages, setPageCurrent }) => {
  function handleClick(event) {
    setPageCurrent(event.target.outerText);
  }

  let active = pageCurrent + 1;
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
