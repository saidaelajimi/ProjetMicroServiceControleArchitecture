-- Données de test initiales
INSERT INTO comptes (numero_compte, proprietaire, solde, type_compte, date_creation)
VALUES ('FR7612345678901', 'Alice Dupont', 5000.0, 'COURANT', CURRENT_TIMESTAMP);

INSERT INTO comptes (numero_compte, proprietaire, solde, type_compte, date_creation)
VALUES ('FR7698765432109', 'Bob Martin', 3500.0, 'EPARGNE', CURRENT_TIMESTAMP);

INSERT INTO comptes (numero_compte, proprietaire, solde, type_compte, date_creation)
VALUES ('FR7611122233344', 'Charlie Durand', 7200.0, 'COURANT', CURRENT_TIMESTAMP);