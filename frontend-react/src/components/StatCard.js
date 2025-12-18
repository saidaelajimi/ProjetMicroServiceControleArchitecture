import React from 'react';

const StatCard = ({ title, value, icon: Icon, color, subtext }) => {
    return (
        <div className="card" style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
            <div style={{
                padding: '1rem',
                borderRadius: '50%',
                background: `${color}15`, /* 15% opacity */
                color: color,
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}>
                <Icon size={24} />
            </div>

            <div>
                <p style={{ color: 'var(--text-muted)', fontSize: '0.875rem', fontWeight: 500 }}>{title}</p>
                <h3 style={{ fontSize: '1.5rem', fontWeight: 700, margin: '0.25rem 0' }}>{value}</h3>
                {subtext && <p style={{ fontSize: '0.75rem', color: 'var(--secondary)' }}>{subtext}</p>}
            </div>
        </div>
    );
};

export default StatCard;
