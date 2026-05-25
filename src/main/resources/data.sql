INSERT INTO users (name, password)
 VALUES ('鈴木一郎', 'himitu'), ('佐藤悠介', 'okusuri'), ('田中愛子', 'check');

 INSERT INTO medicine (name, note, count,m_check, users_id,date,time)
  VALUES
  ( '風邪薬', '食後1回2錠', 2, FALSE, 1,'2026-04-17','09:15'),
  ( '頭痛薬', '食後1回1錠', 1, FALSE, 1,'2026-04-18','09:16'),
  ( 'イブプロフェン', '食後1回1錠', 1, FALSE, 2,'2026-04-19','09:17'),
  ('ビオフェルミン', '食後1回3錠', 3, FALSE,3,'2026-04-20','09:18'),
  ('ロキソニン', '食後1回1錠', 2, FALSE, 1,'2026-04-21','09:19');