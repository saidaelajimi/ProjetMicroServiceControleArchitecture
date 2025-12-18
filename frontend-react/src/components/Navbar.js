import React from 'react';
import { Bell, User } from 'lucide-react';

const Navbar = () => {
    return (
        <header style={{
            height: '70px',
            background: 'white',
            borderBottom: '1px solid #e2e8f0',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'flex-end',
            padding: '0 2rem',
            position: 'sticky',
            top: 0,
            zIndex: 10
        }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
                <button className="btn" style={{ padding: '0.5rem', background: 'transparent' }}>
                    <Bell size={20} color="var(--text-muted)" />
                </button>

                <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                    <div style={{ textAlign: 'right' }}>
                        <p style={{ fontWeight: 600, fontSize: '0.875rem' }}>Alice Dupont</p>
                        <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Client Premium</p>
                    </div>
                    <div style={{
                        width: '40px',
                        height: '40px',
                        borderRadius: '50%',
                        background: 'var(--bg-main)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        border: '1px solid #e2e8f0'
                    }}>
                        <User size={20} color="var(--primary)" />
                    </div>
                </div>
            </div>
        </header>
    );
};

export default Navbar;
