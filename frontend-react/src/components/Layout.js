import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import Navbar from './Navbar';

const Layout = () => {
    return (
        <div style={{ display: 'flex', minHeight: '100vh' }}>
            <Sidebar />
            <main style={{
                flex: 1,
                marginLeft: '260px', /* Sidebar Width */
                backgroundColor: 'var(--bg-main)',
                minHeight: '100vh',
                display: 'flex',
                flexDirection: 'column'
            }}>
                <Navbar />
                <div className="container fade-in" style={{ flex: 1 }}>
                    <Outlet />
                </div>
            </main>
        </div>
    );
};

export default Layout;