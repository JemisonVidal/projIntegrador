import React from "react";

const currency = new Intl.NumberFormat("pt-BR", {
  style: "currency",
  currency: "BRL"
});

export const currencyFormatter = (v) => v && currency.format(v);

export const linkFormatter = (v) => (
  <a href={v} target="blank">
    {v}
  </a>
);

export const locationFormatter = (v) => (
  <span>
    <i className="fa fa-map-marker mr-1" aria-hidden="true"></i>
    {v}
  </span>
);

export const phoneFormatter = (v) =>
  v && `+55 (${v.substring(0, 2)}) ${v.substring(2, 7)}-${v.substring(7)}`;

export const dateFormatter = (v) =>
  v &&
  new Date(v).toLocaleDateString("pt-BR", {
    timeZone: "GMT"
  });
