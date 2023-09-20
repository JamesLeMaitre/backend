-- THE ADMIN
INSERT INTO public.auth_user(id, tracking_id, email,firstname,lastname,phone,password,temp_pwd,temp_pwd_expired_at,enabled,deleted,account_non_expired, account_non_locked, credentials_non_expired, password_changed_once, access_token_expired_at,created_by,created_at,last_modified_by,last_modified_at)
VALUES('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', '18863ae7-5b1c-4157-8a12-4a287d652e66', 'system@system.com', 'System','SYSTEM', '+22990099009', '$2a$10$bHlbCspkD0GR/do6X0qt5ec4KLvHc1iT5k6fhEuzfFOJKv1OLqL1W', null, '2023-05-28 16:31:36.220567', true, false,true, true, true, true, '2024-05-28 16:31:36.220567', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', '2021-05-28 16:31:36.220567', null, null);

-- App event table
DELETE FROM public.auth_app_event;
INSERT INTO public.auth_app_event(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', 'd9ce600d-1629-4e2f-b6c0-14959c1fc430', 'registration', 'Envoi par mail de code de validation de compte.',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_app_event(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'b53b2005-0bde-4ebc-991c-d72c83134611', 'email_reset', 'Envoi par mail de code de confirmation pour changement d adresse email.', false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_app_event(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('c73d18b3-65ad-47fc-95f1-8189fd2a3b36', 'e3e08e0b-6de4-43f1-89d9-240788dbb5fe', 'password_reset_no_auth', 'Envoi par mail de mot de passe temporaire de confirmation pour changement de mot de passe.', false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_app_event(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('1b7ef1ae-88cd-4411-8a69-56416f0e8893', 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'send_account_credentials', 'Envoi par mail des identifiants de connexions.', false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);


-- System variables table
DELETE FROM public.auth_system_variable;
INSERT INTO public.auth_system_variable(id, tracking_id, key, value, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', '9144f6e7-72dd-4e69-865f-af4f7f96417a', 'tempPwdResetDuration', 60, 'Duree de consideration du mot de passe temporaire.' ,  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);

-- Role table
DELETE FROM public.auth_role;
INSERT INTO public.auth_role(id, tracking_id, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', 'd8cfd7a9-b3fd-4813-9c52-7c9204823e8d', 'DEV', 'Le developpeur administrateur' ,  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_role(id, tracking_id, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', '3ab9b86f-aa3e-4e6e-8b7f-3a95f65f1fb1', 'ADMIN', 'Le role d''administrateur du point de vue application et utilisateurs.',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_role(id, tracking_id, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('c73d18b3-65ad-47fc-95f1-8189fd2a3b36', 'faa1fffd-8dc7-43a3-b20a-13f2d5c04364', 'USER', 'Le role d''utilisateurs simples comme les clients, caissiers, comptables etc ...',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);

-- Scope table
DELETE FROM public.auth_scope;
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', 'a7e20d33-06f3-45e7-ae7f-d8d4a7a04b66', 'create', 'Autorisation de creation',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', '8d17eb8b-9c0e-47b4-8b34-f1c8ff6a10a1', 'view', 'Autorisation de lecture',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('c73d18b3-65ad-47fc-95f1-8189fd2a3b36', 'f50e3c9a-389f-4a49-9e0f-65a1bb43a9b1', 'update', 'Autorisation de mise a jour',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', '912ec803-b92c-44a6-b14c-1a2920aeb6e1', 'delete', 'Autorisation de suppression',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('1b7ef1ae-88cd-4411-8a69-56416f0e8893', '532d4e9b-d102-4bdc-8d11-7f2a36b4065a', 'review', 'Autorisation d examination',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('e85f3fe1-51a1-4c46-8aa7-289e3d0a7219', 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'approve', 'Autorisation d approbation',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_scope(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('d0e7084b-4be6-4d10-bfbf-02b20e68dc1b', '12cd93d4-3c12-491a-9e9d-eb18439b74ef', 'disable', 'Autorisation d activation et de desactivation temporaire',  false, 'system@system.com','2023-05-28 16:31:36.220567', null, null);


-- Ressource table
DELETE FROM public.auth_ressource;
INSERT INTO public.auth_ressource(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f', '18863ae7-5b1c-4157-8a12-4a287d652e66', 'auth.service', 'Service d authentification et d authorisation',  false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
INSERT INTO public.auth_ressource(id, tracking_id,  name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
VALUES ('a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'd8cfd7a9-b3fd-4813-9c52-7c9204823e8d', 'email.service', 'Service d envoi  de mails',  false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);



-- Etat table
-- DELETE FROM public.ddb_common_etat;
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', 'd1e2f3a4-b5c6-7d8e-9f0a-b1c2d3e4f5', 'ET-0', 'REÇU', "Etat de reception", false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'd8cfd7a9-b3fd-4813-9c52-7c9204823e8d', 'ET-1', 'ENREGISTRE', "Etat d'enregistrement", false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('d8cfd7a9-b3fd-4813-9c52-7c9204823e8d', 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', 'ET-2', 'APPROUVE', "Etat d'approbation", false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('354198ab-fd6e-4c3a-bb09-8e627c1d90e2', '66778899-aa55-44bb-ccdd-111122223333', 'ET-3', 'CONFIRME', "Etat d'approbation", false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', '912ec803-b92c-44a6-b14c-1a2920aeb6e1', 'ET-4', 'REJETE', 'Etat de rejet', false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('912ec803-b92c-44a6-b14c-1a2920aeb6e1', 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', 'ET-5', 'CLOTURE', 'Etat de cloture', false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('6f5d4c3b-2a1e-9876-b654-1a2b3c4d5e6f', 'e8f7d6c5-4b3a-2109-8e7d-6f5e4d3c2b1', 'ET-6', 'TRAITE', 'Etat de traitement', false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.ddb_common_etat(id, tracking_id, code, name, description, deleted, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('9e8d7c6b-5a4f-3e2d-b1a0-9876543210ab', '1f2e3d4c-a5b6-6c7d-8e9f-0a1b2c3d4e5f', 'ET-7', 'TRANSMIS', 'Etat de transmission', false, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);




-- WorkflowContribution table
-- DELETE FROM public.rc_workflow_contribution;
-- Gestionnaire RC
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', 'd1e2f3a4-b5c6-7d8e-9f0a-b1c2d3e4f5', "GESTIONNAIRE RC", 11, 'REÇUE', false, true, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', 'c38e4b0a-71d9-45f0-bd0c-cd50e7a8e9d8', null, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('c38e4b0a-71d9-45f0-bd0c-cd50e7a8e9d8', '9a8b7c6d-5e4f-3a2b-1c0d-9e8f7a6b5c4', "GESTIONNAIRE RC", 12, 'ENREGISTREE', false, true, 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('f87a9c3b-8e25-4f67-9a12-61ebf72a4d99', '12345678-1234-5678-1234-567812345678', "GESTIONNAIRE RC", 13, 'REJETEE', false, true, 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', null, '5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('6f5d4c3b-2a1e-9876-b654-1a2b3c4d5e6f', 'e8f7d6c5-4b3a-2109-8e7d-6f5e4d3c2b1', "GESTIONNAIRE RC", 14, 'CLOTUREE', false, true, '912ec803-b92c-44a6-b14c-1a2920aeb6e1', null, null, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- CHEF SIDE
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '12ab34cd-56ef-7890-12ab-34cd56ef7890', "CHEF SIDE", 21, 'REÇUE', false, true, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', 'b392e0c6-5478-492d-af25-6a81d49e738c', 'f87a9c3b-8e25-4f67-9a12-61ebf72a4d99', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('b392e0c6-5478-492d-af25-6a81d49e738c', 'b0a1c2e3-d4f5-6071-8293-a4b5c6d7e8f9', "CHEF SIDE", 22, 'CONFIRMEE', false, true, '354198ab-fd6e-4c3a-bb09-8e627c1d90e2', '8d9e7c5a-6b12-4f3d-8e9f-1c7a2b0d3e4f', '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('e671f540-03e8-4b4e-992a-b556f17cf42d', '1a2b3c4d-5e6f-7081-9203-a4b5c6d7e8f9', "CHEF SIDE", 23, 'REJETEE', false, true, 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', null, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- COMITE DE VALIDATION
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('8d9e7c5a-6b12-4f3d-8e9f-1c7a2b0d3e4f', 'f0e1d2c3-9876-5432-aaee-ffeeddccbbaa', "COMITE DE VALIDATION", 31, 'REÇUE', false, true, 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', '354198ab-fd6e-4c3a-bb09-8e627c1d90e2', 'b392e0c6-5478-492d-af25-6a81d49e738c', 'e671f540-03e8-4b4e-992a-b556f17cf42d', '2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('354198ab-fd6e-4c3a-bb09-8e627c1d90e2', '66778899-aa55-44bb-ccdd-111122223333', "COMITE DE VALIDATION", 32, 'VALIDEE', false, true, 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', '354198ab-fd6e-4c3a-bb09-8e627c1d90e2', '8d9e7c5a-6b12-4f3d-8e9f-1c7a2b0d3e4f', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('d80a6b4c-956f-4928-af73-ec1d2e0b5f68', 'a1b2c3d4-e5f6-6a7b-8c9d-0e1f2a3b4c5d', "COMITE DE VALIDATION", 33, 'REJETEE', false, true, 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', null, '8d9e7c5a-6b12-4f3d-8e9f-1c7a2b0d3e4f', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);



-- WorkflowConsultation table
-- DELETE FROM public.rc_workflow_consultation;
-- Gestionnaire RC
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', 'd1e2f3a4-b5c6-7d8e-9f0a-b1c2d3e4f5', "GESTIONNAIRE RC", 11, 'REÇUE', false, true, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', 'c38e4b0a-71d9-45f0-bd0c-cd50e7a8e9d8', null, '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('c38e4b0a-71d9-45f0-bd0c-cd50e7a8e9d8', '9a8b7c6d-5e4f-3a2b-1c0d-9e8f7a6b5c4', "GESTIONNAIRE RC", 12, 'TRAITEE', false, true, 'a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11', null, '5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('f87a9c3b-8e25-4f67-9a12-61ebf72a4d99', '12345678-1234-5678-1234-567812345678', "GESTIONNAIRE RC", 13, 'REJETEE', false, true, 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', null, '5c3a17b1-0f6e-4a9b-b94e-78f26e2a1b22', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- CHEF SIDE
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '12ab34cd-56ef-7890-12ab-34cd56ef7890', "CHEF SIDE", 21, 'REÇUE', false, true, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', 'b392e0c6-5478-492d-af25-6a81d49e738c', 'c38e4b0a-71d9-45f0-bd0c-cd50e7a8e9d8', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('b392e0c6-5478-492d-af25-6a81d49e738c', 'b0a1c2e3-d4f5-6071-8293-a4b5c6d7e8f9', "CHEF SIDE", 22, 'CONFIRMEE', false, true, '354198ab-fd6e-4c3a-bb09-8e627c1d90e2', null, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('8b7a6d5c-4e3f-2a1b-d0c9-876543210987', '1e2d3c4b-5f6a-7089-9b8c-d7e6f5a4b3c2', "CHEF SIDE", 23, 'TRANSMISE', false, true, '9e8d7c6b-5a4f-3e2d-b1a0-9876543210ab', null, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
-- INSERT INTO public.rc_workflow_contribution(id, tracking_id, station, stationNumber, decision, demandProcessed, applicantNotifiedDemandSaved, etat_id, next_id, prev_id, created_by, created_at, last_modified_by, last_modified_at)
-- VALUES ('e671f540-03e8-4b4e-992a-b556f17cf42d', '1a2b3c4d-5e6f-7081-9203-a4b5c6d7e8f9', "CHEF SIDE", 24, 'REJETEE', false, true, 'f5db071d-8dd2-4f96-9aeb-c9d4ac2ff8a4', null, '2e4c6f8d-1a3b-40e9-85c7-93d5b6a7c8f2', '3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f','2023-05-28 16:31:36.220567', null, null);
