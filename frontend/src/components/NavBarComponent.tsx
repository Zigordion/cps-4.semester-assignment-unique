import React from 'react'
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
const NavBarComponent = () => {
    return (
        <AppBar position="static" sx={{ backgroundColor: '#98CEFF' }}>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <Typography
                    variant="h6"
                    noWrap
                    component="a"
                    href="/"
                    sx={{
                        mr: 2,
                        display: { xs: 'none', md: 'flex' },
                        fontFamily: 'monospace',
                        fontWeight: 700,
                        letterSpacing: '.3rem',
                        color: 'inherit',
                        textDecoration: 'none',
                    }}
                    >
                    HOME
                    </Typography>
                </Toolbar>
            </Container>
        </AppBar>
    )
}

export default NavBarComponent