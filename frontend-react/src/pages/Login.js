import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Coins } from 'lucide-react';

const Login = () => {
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        // Simulate login
        navigate('/dashboard');
    };

    return (
        <div style={{
            minHeight: '100vh',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            background: 'var(--primary)'
        }}>
            <div className="card fade-in" style={{ width: '100%', maxWidth: '400px', padding: '2.5rem' }}>
                <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
                    <div style={{
                        display: 'inline-flex',
                        padding: '1rem',
                        background: '#eff6ff',
                        borderRadius: '50%',
                        marginBottom: '1rem'
                    }}>
                        <Coins size={32} color="var(--primary)" />
                    </div>
                    <h2>Connexion</h2>
                    <p style={{ color: 'var(--text-muted)' }}>Accédez à votre espace bancaire</p>
                </div>

                <form onSubmit={handleLogin} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: 500 }}>
                            Identifiant client
                        </label>
                        <input
                            type="text"
                            defaultValue="76123456"
                            style={{
                                width: '100%',
                                padding: '0.75rem',
                                borderRadius: '0.5rem',
                                border: '1px solid #cbd5e1',
                                outline: 'none'
                            }}
                        />
                    </div>

                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: 500 }}>
                            Mot de passe
                        </label>
                        <input
                            type="password"
                            defaultValue="password"
                            style={{
                                width: '100%',
                                padding: '0.75rem',
                                borderRadius: '0.5rem',
                                border: '1px solid #cbd5e1',
                                outline: 'none'
                            }}
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ marginTop: '0.5rem' }}>
                        Se connecter
                    </button>
                </form>

                <p style={{ marginTop: '1.5rem', textAlign: 'center', fontSize: '0.875rem', color: 'var(--text-muted)' }}>
                    Pas encore client ? <a href="#" style={{ color: 'var(--secondary)', textDecoration: 'none', fontWeight: 500 }}>Ouvrir un compte</a>
                </p>
            </div>
        </div>
    );
};

export default Login;