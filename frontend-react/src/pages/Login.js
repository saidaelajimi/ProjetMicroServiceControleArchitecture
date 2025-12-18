import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Coins, AlertCircle } from 'lucide-react';
import api from '../services/api';

const Login = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const response = await api.post('http://localhost:8080/auth/login', {
                username: formData.username,
                password: formData.password
            });

            // Store JWT token in localStorage
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('username', response.data.username);

            // Redirect to dashboard
            navigate('/dashboard');
        } catch (err) {
            console.error('Login error:', err);
            if (err.response?.status === 401) {
                setError('Identifiants incorrects');
            } else {
                setError('Erreur de connexion au serveur');
            }
        } finally {
            setLoading(false);
        }
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

                {error && (
                    <div style={{ padding: '1rem', background: '#fef2f2', border: '1px solid #fee2e2', borderRadius: '0.5rem', marginBottom: '1rem', display: 'flex', alignItems: 'center', gap: '0.5rem', color: 'var(--danger)' }}>
                        <AlertCircle size={20} />
                        <span>{error}</span>
                    </div>
                )}

                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: 500 }}>
                            Nom d'utilisateur
                        </label>
                        <input
                            type="text"
                            required
                            value={formData.username}
                            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                            placeholder="admin ou user"
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
                            required
                            value={formData.password}
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            placeholder="admin123 ou user123"
                            style={{
                                width: '100%',
                                padding: '0.75rem',
                                borderRadius: '0.5rem',
                                border: '1px solid #cbd5e1',
                                outline: 'none'
                            }}
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ marginTop: '0.5rem' }} disabled={loading}>
                        {loading ? 'Connexion...' : 'Se connecter'}
                    </button>
                </form>

                <p style={{ marginTop: '1.5rem', textAlign: 'center', fontSize: '0.875rem', color: 'var(--text-muted)' }}>
                    <strong>Démo:</strong> admin/admin123 ou user/user123
                </p>
            </div>
        </div>
    );
};

export default Login;