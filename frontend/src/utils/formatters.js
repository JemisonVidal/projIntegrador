import React from 'react';

const currency = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

export const currencyFormatter = (v) => v && currency.format(v);

export const linkFormatter = (v) => <a href={v} target="blank">{v}</a>;