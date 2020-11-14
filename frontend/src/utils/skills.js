export const skillMap = {
  BASIC: 'Básico',
  INTERMEDIATE: 'Intermediário',
  ADVANCED: 'Avançado',
};

export const skillOptions = Object.entries(skillMap).reduce(
  (acc, [k, v]) => acc.concat({ value: k, text: v }),
  []
);
