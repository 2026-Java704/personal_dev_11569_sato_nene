INSERT INTO users (name, password)
 VALUES ('鈴木一郎', 'himitu'), ('佐藤悠介', 'okusuri'), ('田中愛子', 'check');

 INSERT INTO medicine (name, note, count, medicine_type, timing, meal_timing, m_check, users_id,date,time)
  VALUES
  ( '風邪薬', '熱が高い時だけ飲む', 2, '処方薬', '朝', '食後', FALSE, 1, '2026-04-17', NULL),
  ( '頭痛薬', 'つらい時のために登録', 1, '市販薬', '昼', '食後', FALSE, 1, '2026-04-18', NULL),
  ( 'イブプロフェン', 'のどが痛い時用', 1, '処方薬', '夜', '食後', FALSE, 2, '2026-04-19', NULL),
  ('ビオフェルミン', 'お腹の調子を見る', 3, '処方薬', '朝', '食前', TRUE, 3, '2026-04-20', '08:10'),
  ('ロキソニン', '痛みが強い時のメモ', 2, '市販薬', '夜', '食後', TRUE, 1, '2026-04-21', '21:05');