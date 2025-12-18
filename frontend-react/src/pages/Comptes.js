import React, { useEffect, useState } from 'react';
import api from '../services/api';

const Comptes = () => {
    // This is basically a full transaction history view
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const userNumCompte = "FR7612345678901";

    useEffect(() => {
        const fetchHistory = async () => {
            try {
                const res = await api.get(`/transactions/compte/${userNumCompte}`);
                setTransactions(res.data);
            } catch (error) {
                console.error("Erreur historique:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchHistory();
    }, []);

    if (loading) return <div>Chargement...</div>;

    return (
        <div className="fade-in">
            <h1 style={{ marginBottom: '2rem' }}>Historique des Transactions</h1>
            <div className="card">
                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr style={{ textAlign: 'left', borderBottom: '1px solid #e2e8f0', color: 'var(--text-muted)' }}>
                            <th style={{ padding: '1rem' }}>ID</th>
                            <th style={{ padding: '1rem' }}>Date</th>
                            <th style={{ padding: '1rem' }}>Description</th>
                            <th style={{ padding: '1rem', textAlign: 'right' }}>Montant</th>
                            <th style={{ padding: '1rem', textAlign: 'center' }}>Statut</th>
                        </tr>
                    </thead>
                    <tbody>
                        {transactions.map((t) => (
                            <tr key={t.id} style={{ borderBottom: '1px solid #f1f5f9' }}>
                                <td style={{ padding: '1rem', color: 'var(--text-muted)' }}>#{t.id}</td>
                                <td style={{ padding: '1rem' }}>{new Date(t.dateTransaction).toLocaleString()}</td>
                                <td style={{ padding: '1rem' }}>{t.description}</td>
                                <td style={{ padding: '1rem', textAlign: 'right', fontWeight: 600 }}>
                                    {t.montant.toFixed(2)} €
                                </td>
                                <td style={{ padding: '1rem', textAlign: 'center' }}>
                                    <span style={{
                                        padding: '0.25rem 0.5rem',
                                        borderRadius: '0.25rem',
                                        fontSize: '0.75rem',
                                        background: t.statut === 'SUCCESS' ? '#dcfce7' : '#fee2e2',
                                        color: t.statut === 'SUCCESS' ? '#166534' : '#991b1b'
                                    }}>
                                        {t.statut}
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Comptes;