"""
Unit03 DAO 講義HTMLから印刷用HTMLを生成し、Chrome headless で PDF を出力する。

前提: Google Chrome がインストールされていること（Windows 既定パスを探索）。
"""
from __future__ import annotations

import pathlib
import subprocess
import sys


def find_chrome_exe() -> pathlib.Path | None:
	candidates = [
		pathlib.Path(r"C:\Program Files\Google\Chrome\Application\chrome.exe"),
		pathlib.Path(r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"),
	]
	for p in candidates:
		if p.is_file():
			return p
	return None


def main() -> int:
	root = pathlib.Path(__file__).resolve().parents[1]
	src = root / "Unit03_DAO" / "01-テキスト-DAOパターンと安全性.html"
	if not src.exists():
		print("SOURCE_NOT_FOUND", src, file=sys.stderr)
		return 1

	raw = src.read_text(encoding="utf-8")
	lines = raw.splitlines()
	if not lines:
		print("EMPTY_SOURCE", file=sys.stderr)
		return 1

	title = lines[0].strip()
	body_inner = "\n".join(lines[1:]).strip() + "\n"

	# 印刷時に details の中身が出るよう、すべて open にする（JSは使わない）
	body_inner = body_inner.replace("<details ", "<details open ")

	# PDF用は実効幅を広げる（本編の max-width: 800px を緩める）
	body_inner = body_inner.replace(
		"max-width: 800px; margin: 0 auto;",
		"max-width: 100%; margin: 0 auto;",
		1,
	)

	# PDF生成用HTML（src/template.html に出力）
	pdf_html = root / "Unit03_DAO" / "src" / "template.html"
	doc = f"""<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<title>{title}</title>
<link rel="stylesheet" href="../../assets/pdf-a4.css">

</head>
<body>
{body_inner}
</body>
</html>
"""
	pdf_html.write_text(doc, encoding="utf-8")
	print("WROTE", pdf_html)

	chrome = find_chrome_exe()
	if chrome is None:
		print("CHROME_NOT_FOUND: PDFは手動で「Unit03_DAO/src/template.html」を開き印刷してください。", file=sys.stderr)
		return 0

	out_pdf = root / "Unit03_DAO" / "99-テキストpdf-DAOパターンと安全性.pdf"
	url = pdf_html.resolve().as_uri()

	cmd = [
		str(chrome),
		"--headless=new",
		"--disable-gpu",
		f"--print-to-pdf={out_pdf}",
		"--no-pdf-header-footer",
		url,
	]
	print("RUN", " ".join(cmd))
	subprocess.run(cmd, check=True)
	print("WROTE", out_pdf)
	return 0


if __name__ == "__main__":
	raise SystemExit(main())

