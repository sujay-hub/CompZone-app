import React from 'react';
import { Container } from '@mui/material';
import { Outlet } from 'react-router-dom';
import NavBar from './NavBar';
import PropTypes from 'prop-types';

function Layout({cartCount}) {
  return (
    <>
      <NavBar cartCount={cartCount} />
      <Container sx={{ mt: 4 }}>
        <Outlet />
      </Container>
    </>
  )
}
Layout.propTypes = {
  cartCount: PropTypes.number.isRequired,
};

export default Layout