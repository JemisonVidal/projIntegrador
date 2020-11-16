import React from 'react';
import AddOpportunity from '../../components/AddOpportunity/AddOpportunity';
import Main from '../../components/Template/main/Main';


const PagesAddOpportunity = (props) => {
  return (
    <Main>
      <AddOpportunity 
        id={ props.computedMatch.params.id }
        type={ props.computedMatch.params.type }
      />
    </Main>
  );
};

export default PagesAddOpportunity;
