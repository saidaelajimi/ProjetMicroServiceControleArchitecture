import React, { useEffect, useState } from 'react';
import { ArrowRight, RefreshCw, TrendingUp } from 'lucide-react';
import api from '../services/api';

const TauxChange = () => {
    const [rates, setRates] = useState(null);
    const [amount, setAmount] = useState(1);
    const [conversion, setConversion] = useState(null);
    const [loading, setLoading] = useState(true);

    // Default Fetch Rates for USD base
    const fetchRates = async () => {
        setLoading(true);
        try {
            // Assuming reporting service is running
            // Using 'USD' as base for demo or fetching based on user pref
            const res = await api.get('/reporting/taux-change/USD');
            setRates(res.data);
        } catch (error) {
            console.error("Erreur taux de change:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRates();
    }, []);

    const handleConvert = async () => {
        if (!amount) return;
        try {
            const res = await api.get(`/reporting/convertir?montant=${amount}&deviseSource=USD&deviseCible=EUR`);
            setConversion(res.data);
        } catch (error) {
            console.error("Erreur conversion:", error);
        }
    };

    return (
        <div className="fade-in">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h1>Taux de Change</h1>
                <button className="btn btn-secondary" onClick={fetchRates}>
                    <RefreshCw size={18} style={{ marginRight: '0.5rem' }} />
                    Actualiser
                </button>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '2rem' }}>

                {/* Converter Section */}
                <div className="card">
                    <h3 style={{ marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <TrendingUp size={20} color="var(--primary)" />
                        Convertisseur Rapide
                    </h3>

                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1.5rem' }}>
                        <div style={{ flex: 1 }}>
                            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem' }}>Montant (USD)</label>
                            <input
                                type="number"
                                value={amount}
                                onChange={(e) => setAmount(e.target.value)}
                                className="input"
                                style={{ width: '100%', padding: '0.75rem', borderRadius: '0.5rem', border: '1px solid #cbd5e1' }}
                            />
                        </div>
                        <ArrowRight size={24} color="var(--text-muted)" style={{ marginTop: '1.5rem' }} />
                        <div style={{ flex: 1 }}>
                            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem' }}>Résultat (EUR)</label>
                            <div style={{
                                padding: '0.75rem',
                                background: '#f1f5f9',
                                borderRadius: '0.5rem',
                                fontWeight: 600,
                                height: '42px',
                                display: 'flex',
                                alignItems: 'center'
                            }}>
                                {conversion ? `${conversion.montantConverti?.toFixed(2)} €` : '---'}
                            </div>
                        </div>
                    </div>

                    <button className="btn btn-primary" style={{ width: '100%' }} onClick={handleConvert}>
                        Convertir
                    </button>
                </div>

                {/* Top Rates Section */}
                <div className="card" style={{ background: 'var(--primary)', color: 'white' }}>
                    <h3 style={{ color: 'white', marginBottom: '1rem' }}>Taux du Marché</h3>
                    <p style={{ opacity: 0.7, fontSize: '0.875rem', marginBottom: '1.5rem' }}>Base: 1 USD</p>

                    {loading ? (
                        <p>Chargement...</p>
                    ) : rates?.taux ? (
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                            {/* Demo specific rates since we might get many */}
                            <div style={{ display: 'flex', justifyContent: 'space-between', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '0.5rem' }}>
                                <span>🇪🇺 EUR</span>
                                <span style={{ fontWeight: 600 }}>{rates.taux['EUR'] || 'N/A'}</span>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '0.5rem' }}>
                                <span>🇬🇧 GBP</span>
                                <span style={{ fontWeight: 600 }}>{rates.taux['GBP'] || 'N/A'}</span>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '0.5rem' }}>
                                <span>🇯🇵 JPY</span>
                                <span style={{ fontWeight: 600 }}>{rates.taux['JPY'] || 'N/A'}</span>
                            </div>
                        </div>
                    ) : (
                        <p className="text-sm">Impossible de charger les taux.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default TauxChange;