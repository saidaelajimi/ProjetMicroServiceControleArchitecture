import React, { useEffect, useState } from 'react';
import { Wallet, TrendingUp, ArrowDownRight, ArrowUpRight } from 'lucide-react';
import StatCard from '../components/StatCard';
import api from '../services/api';

const Dashboard = () => {
    const [comptes, setComptes] = useState([]);
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);

    // Hardcoded user implementation for demo (Alice)
    const userNumCompte = "FR7612345678901";

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch Account Details
                const compteRes = await api.get(`/comptes/search/byNumero?numeroCompte=${userNumCompte}`);
                setComptes([compteRes.data]);

                // Fetch Recent Transactions
                const transRes = await api.get(`/transactions/compte/${userNumCompte}`);
                // Sort by date desc and take top 5
                const sorted = transRes.data.sort((a, b) => new Date(b.dateTransaction) - new Date(a.dateTransaction));
                setTransactions(sorted.slice(0, 5));

            } catch (error) {
                console.error("Erreur chargement dashboard:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    if (loading) return <div className="fade-in">Chargement...</div>;

    const totalSolde = comptes.reduce((acc, c) => acc + c.solde, 0);

    return (
        <div className="fade-in">
            <h1 style={{ marginBottom: '2rem' }}>Tableau de bord</h1>

            {/* Stats Grid */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '1.5rem', marginBottom: '2rem' }}>
                <StatCard
                    title="Solde Total"
                    value={`${totalSolde.toFixed(2)} €`}
                    icon={Wallet}
                    color="var(--primary)"
                    subtext="+2.5% ce mois"
                />
                <StatCard
                    title="Revenus"
                    value="4,250.00 €"
                    icon={TrendingUp}
                    color="var(--secondary)"
                    subtext="Depuis le 1er"
                />
                <StatCard
                    title="Dépenses"
                    value="1,120.50 €"
                    icon={ArrowDownRight}
                    color="var(--danger)"
                    subtext="-12% vs dernier mois"
                />
            </div>

            {/* Recent Transactions */}
            <div className="card">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
                    <h3>Transactions Récentes</h3>
                    <button className="btn" style={{ color: 'var(--accent)', padding: 0 }}>Voir tout</button>
                </div>

                <div style={{ overflowX: 'auto' }}>
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ textAlign: 'left', borderBottom: '1px solid #e2e8f0', color: 'var(--text-muted)' }}>
                                <th style={{ padding: '1rem' }}>Date</th>
                                <th style={{ padding: '1rem' }}>Description</th>
                                <th style={{ padding: '1rem' }}>Type</th>
                                <th style={{ padding: '1rem', textAlign: 'right' }}>Montant</th>
                                <th style={{ padding: '1rem', textAlign: 'center' }}>Statut</th>
                            </tr>
                        </thead>
                        <tbody>
                            {transactions.map((t) => (
                                <tr key={t.id} style={{ borderBottom: '1px solid #f1f5f9' }}>
                                    <td style={{ padding: '1rem' }}>{new Date(t.dateTransaction).toLocaleDateString()}</td>
                                    <td style={{ padding: '1rem', fontWeight: 500 }}>{t.description || "Virement"}</td>
                                    <td style={{ padding: '1rem' }}>
                                        <span style={{
                                            padding: '0.25rem 0.75rem',
                                            borderRadius: '1rem',
                                            fontSize: '0.75rem',
                                            background: '#f1f5f9',
                                            color: 'var(--text-muted)'
                                        }}>
                                            {t.type}
                                        </span>
                                    </td>
                                    <td style={{ padding: '1rem', textAlign: 'right', fontWeight: 600, color: t.type === 'DEPOT' ? 'var(--secondary)' : 'var(--text-main)' }}>
                                        {t.type === 'RETRAIT' || (t.type === 'VIREMENT' && t.compteSourceNumero === userNumCompte) ? '-' : '+'}
                                        {t.montant.toFixed(2)} €
                                    </td>
                                    <td style={{ padding: '1rem', textAlign: 'center' }}>
                                        <span style={{
                                            padding: '0.25rem 0.75rem',
                                            borderRadius: '1rem',
                                            fontSize: '0.75rem',
                                            fontWeight: 600,
                                            background: t.statut === 'SUCCESS' ? '#ecfdf5' : '#fef2f2',
                                            color: t.statut === 'SUCCESS' ? 'var(--secondary)' : 'var(--danger)'
                                        }}>
                                            {t.statut}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                            {transactions.length === 0 && (
                                <tr>
                                    <td colSpan="5" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>
                                        Aucune transaction récente.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;