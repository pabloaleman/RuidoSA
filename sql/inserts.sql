--configuracion
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (1, 'PATH_LOCAL', 'C:\\archivosSubidos', 'El path donde se guardaran los archivos binarios que vienen de las estaciones');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (2, 'FORMATO_FECHA', 'dd/MM/yyyy HH:mm:ss', 'El formato de la fecha de los archivos');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (3, 'HORAS_DIARIO', '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23', 'Las horas para calcular el promedio diario');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (4, 'HORAS_PROMEDIO_DIA', '7,8,9,10,11,12,13,14,15,16,17,18,19,20', 'Las horas para calcular el promedio de día');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (5, 'HORAS_PROMEDIO_NOCHE', '0,1,2,3,4,5,6,21,22,23', 'Las horas para calcular el promedio de noche');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (6, 'N_DATOS_MINIMO_DIARIO', '18', 'El número de datos mínimo para calcular el promedio diario');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (7, 'N_DATOS_MINIMO_PROMEDIO_DIA', '11', 'El número de datos mínimo para calcular el promedio de día');
INSERT INTO public.configuracion(id, item, valor, descripcion) VALUES (8, 'N_DATOS_MINIMO_PROMEDIO_NOCHE', '7', 'El número de datos mínimo para calcular el promedio de noche');

--estaciones
INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (1, 'E1', 'Activa', 'Cotocollao');
INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (2, 'E2', 'Activa', 'Carapungo');
INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (4, 'E4', 'Activa', 'Jipijapa');
INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (5, 'E5', 'Activa', 'El Camal');
INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (6, 'E6', 'Activa', 'Centro');
--INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (3, 'E3', 'Activa', '');
--INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (7, 'E7', 'Activa', '');
--INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (8, 'E8', 'Activa', '');
--INSERT INTO public.estacion(id, acronimo, estado, nombre) VALUES (9, 'E9', 'Activa', '');

--catalogo de mensajes
INSERT INTO public.mensajes(id, acronimo, descripcion) VALUES (1, 'error0001', 'El archivo no tiene datos');
INSERT INTO public.mensajes(id, acronimo, descripcion) VALUES (2, 'error0002', 'El archivo es inválido');

--catalogo canal
/*INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (1, 'Ch1 (SLM)', 'Canal 1', 'CANAL');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (2, 'Ch2 (SLM)', 'Canal 2', 'CANAL');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (3, 'Ch3 (SLM)', 'Canal 3', 'CANAL');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (4, 'P1 (A)', 'Perfil 1', 'PERFIL');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (5, 'P1 (A, Slow)', 'Perfil 1A', 'PERFIL');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (6, 'LApeak [dB]', 'Medida', 'MEDIDA');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (7, 'LASmax [dB]', 'Medida', 'MEDIDA');*/
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (8, 'svanPC', 'SvanPC', 'PLATAFORMA');
--INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (9, 'plat2', 'Plataforma 2', 'PLATAFORMA');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (10, '1h', 'Promedio horario', 'TIPO_DATO');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (11, '24h', 'Promedio diario', 'TIPO_DATO');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (16, 'pd', 'Promedio dia', 'TIPO_DATO');
INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (17, 'pn', 'Promedio noche', 'TIPO_DATO');

INSERT INTO public.catalogo(id, acronimo, descripcion, tipo)VALUES (-1, 'NAN', 'No aplica', 'NIVEL');



--parametro
/*INSERT INTO public.parametro(id, acronimo, aplica_promedio, descripcion, guarda, principal, unidad)
VALUES (1, 'LEQ [dB]', false, 'Parametro 1', true, true, 'db');

INSERT INTO public.parametro(id, acronimo, aplica_promedio, descripcion, guarda, principal, unidad)
VALUES (2, 'P1 (A, Slow)', false, 'Parametro 2', false, false, 'db');

INSERT INTO public.parametro(id, acronimo, aplica_promedio, descripcion, guarda, principal, unidad)
VALUES (3, 'LApeak [dB]', false, 'Parametro 3', false, true, 'db');*/

--usuario
INSERT INTO public.usuario(id, estado, foto, nombre, password, username)
VALUES (1, 'Activo', 'admin', 'admin', 'admin', 'admin');

insert into public.rol(id, descripcion, nombre)
values(1, 'ROLE_ADMIN', 'ROLE_ADMIN');
insert into public.rol(id, descripcion, nombre)
values(2, 'ROLE_CARGA', 'ROLE_CARGA');

INSERT INTO public.rol_usuario
values(1, 1);
INSERT INTO public.rol_usuario
values(2, 1);
