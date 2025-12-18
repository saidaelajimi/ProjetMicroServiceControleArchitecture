import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, ArrowRightLeft, History, Coins, LogOut, UserPlus } from 'lucide-react';

const Sidebar = () => {
    const navItems = [
        { path: '/dashboard', icon: LayoutDashboard, label: 'Tableau de bord' },
        { path: '/create-account', icon: UserPlus, label: 'Ouvrir Compte' },
        { path: '/transfert', icon: ArrowRightLeft, label: 'Virement' },
        { path: '/comptes', icon: History, label: 'Mes Comptes' }, // Using Comptes for history/details
        { path: '/change', icon: Coins, label: 'Taux & Change' },
    ];

    return (
        <aside style={{
            width: '260px',
            backgroundColor: 'var(--primary)',
            color: 'white',
            height: '100vh',
            position: 'fixed',
            left: 0,
            top: 0,
            display: 'flex',
            flexDirection: 'column',
            boxShadow: '4px 0 10px rgba(0,0,0,0.1)'
        }}>
            {/* Logo Area */}
            <div style={{ padding: '2rem', borderBottom: '1px solid rgba(255,255,255,0.1)' }}>
                <h2 style={{ color: 'white', fontSize: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    <Coins size={28} color="var(--secondary)" />
                    Bank<span style={{ color: 'var(--secondary)' }}>App</span>
                </h2>
            </div>

            {/* Navigation */}
            <nav style={{ flex: 1, padding: '2rem 1rem' }}>
                <ul style={{ listStyle: 'none' }}>
                    {navItems.map((item) => (
                        <li key={item.path} style={{ marginBottom: '0.5rem' }}>
                            <NavLink
                                to={item.path}
                                style={({ isActive }) => ({
                                    display: 'flex',
                                    alignItems: 'center',
                                    padding: '0.875rem 1rem',
                                    color: isActive ? 'white' : 'rgba(255,255,255,0.6)',
                                    background: isActive ? 'rgba(255,255,255,0.1)' : 'transparent',
                                    borderRadius: '0.5rem',
                                    textDecoration: 'none',
                                    transition: 'all 0.2s',
                                    fontWeight: isActive ? 600 : 400
                                })}
                            >
                                <item.icon size={20} style={{ marginRight: '0.75rem' }} />
                                {item.label}
                            </NavLink>
                        </li>
                    ))}
                </ul>
            </nav>

            {/* Logout Area */}
            <div style={{ padding: '1.5rem', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
                <button
                    className="btn"
                    style={{
                        width: '100%',
                        justifyContent: 'flex-start',
                        color: 'rgba(255,255,255,0.7)',
                        background: 'transparent'
                    }}
                >
                    <LogOut size={20} style={{ marginRight: '0.75rem' }} />
                    Déconnexion
                </button>
            </div>
        </aside>
    );
};

export default Sidebar;
