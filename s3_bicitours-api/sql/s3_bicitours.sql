delete from ComentarioEntity;
delete from BlogEntity;
delete from BlogEntity_UsuarioEntity;
delete from UsuarioEntity; 
delete from SeguroEntity;
delete from FotoEntity;
delete from ComentarioEntity_ComentarioEntity;

insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaImagen) 
values (1,
'My name is Yoshikage Kira',
4,'My name is Yoshikage Kira. I''m 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest...',/**
'My name is Yoshikage Kira. I''m 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest. I don''t smoke, but I occasionally drink.I''m in bed by 11 PM, and make sure I get eight hours of sleep, no matter what. After having a glass of warm milk and doing about twenty minutes of stretches before going to bed, I usually have no problems sleeping until morning. Just like a baby, I wake up without any fatigue or stress in the morning. I was told there were no issues at my last check-up.I''m trying to explain that I''m a person who wishes to live a very quiet life. I take care not to trouble myself with any enemies, like winning and losing, that would cause me to lose sleep at night. That is how I deal with society, and I know that is what brings me happiness. Although, if I were to fight I wouldn''t lose to anyone.',
*/'https://i.kym-cdn.com/entries/icons/original/000/029/929/Kira_decides_to_tail_Shigechi.png');
insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaVideo ) 
values (2,
'the tragedy of Darth Plagueis The Wise',
66.0,'Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It''s not a story the Jedi would tell you. It''s a Sith legend...',/**
'Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It''s not a story the Jedi would tell you. It''s a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to influence the midichlorians to create life… He had such a knowledge of the dark side that he could even keep the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to be unnatural. He became so powerful… the only thing he was afraid of was losing his power, which eventually, of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in his sleep. Ironic. He could save others from death, but not himself.',
*/'https://youtu.be/05dT34hGRdg' );
insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaVideo) 
values (3,
'BiciGo',
9.5,
'Felicidad y salud al alcance de tu bici',
'https://www.youtube.com/watch?v=dQw4w9WgXcQ');
insert into BlogEntity (id, titulo, calificacionPromedio, texto, rutaImagen) 
values (4,
'Azer isn''t so great?',
727.0,'Azer isn''t so great? Are you kidding me? When was the last time you saw a player with such aim ability and movement with a tablet?',/**
'Azer isn''t so great? Are you kidding me? When was the last time you saw a player with such aim ability and movement with a tablet? Alex puts the game in another level, and we will be blessed if we ever see a player with his skill and passion for the game again. Cookiezi breaks records. Rafis breaks records. Azer breaks the rules. You can keep your statistics. I prefer the magic.',
*/'https://upload.wikimedia.org/wikipedia/commons/d/d3/Osu%21Logo_%282015%29.png');

insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (0,'Seguro contra robo de bicicletas','El robo de debe reportar 24 horas despues de sucedido para hacer valido el seguro', 'Antirobo');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (1,'Seguro para fallecimiento','Se hace efectivo si es cliente fallece en un accidente durante el tour', 'Seguro de vida');
insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2,'Seguro para lesiones','Se cubren todos los gastos medicos que requiera el participante tras sufrir una lesion en el tour', 'Seguro de salud');

  
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (100, 'Juan González', 'code1', 'js.gonzalez15@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (200, 'Diego Rueda', 'code2', 'jd.ruedaa@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (300, 'Oscar Castañeda', 'code3', 'oj.castaneda@uniandes.edu.co',1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (400, 'Michel Succar', 'code4', 'm.succar@uniandes.edu.co', 1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (500, 'Maria Clara', 'code5', 'mc.noguera@uniandes.edu.co', 1,1,10);
insert into UsuarioEntity (id, nombre, codigo, correo, pago, esAdmin, deuda) values (600, 'Jhuliana Barrios', 'code6', 'jj.barriosm@uniandes.edu.co',1,1,10);

insert into FotoEntity (id, ruta, tour_id) values (300, 'ruta1', 0);
insert into FotoEntity (id, ruta, tour_id) values (400, 'ruta2', 0);
insert into FotoEntity (id, ruta, tour_id) values (500, 'ruta3', 0);
insert into FotoEntity (id, ruta, tour_id) values (600, 'ruta4', 0);
insert into FotoEntity (id, ruta, tour_id) values (700, 'ruta5', 1);
insert into FotoEntity (id, ruta, tour_id) values (800, 'ruta6', 1);

insert into ComentarioEntity (id, texto, calificacion) values (0, 'El mejor monólogo de todos', 5);
insert into ComentarioEntity (id, texto, calificacion) values (1, 'Nada mal', 4);
insert into ComentarioEntity (id, texto, calificacion) values (2, 'Killer Queen Bites The Dust', 3)