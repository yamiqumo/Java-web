"""
export_common_diagrams_pdfs_to_png.py

`diagrams/` 直下にある汎用図PDF（例: インターネットアクセス/ポート番号）を
PNG（ページ単位）へ変換して、`diagrams/00-exported/common/` に配置する。

成功後に元PDFを削除する。
"""

from __future__ import annotations

import pathlib

import pypdfium2 as pdfium


def label_from_stem(stem: str) -> str:
	# ファイル名が日本語の場合でも、stem に含まれる文字列で判定する
	if "インターネットアクセス" in stem:
		return "internet_access"
	if "ポート番号" in stem:
		return "port_number"
	# fallback: 英語化せず、safe なprefixとしてそのまま（ただしスペース等は置換）
	return stem.replace(" ", "_")


def export_pdf_to_png(pdf_path: pathlib.Path, out_dir: pathlib.Path, *, scale: float, quality: int) -> list[pathlib.Path]:
	out_dir.mkdir(parents=True, exist_ok=True)

	label = label_from_stem(pdf_path.stem)
	created: list[pathlib.Path] = []

	pdf = pdfium.PdfDocument(str(pdf_path))
	try:
		page_count = len(pdf)
		for i in range(page_count):
			page = pdf.get_page(i)
			try:
				pil = page.render(scale=scale).to_pil()
				# PNG出力（quality は任意。Pillow のPNGは通常パラメータが異なるため set しない）
				out = out_dir / f"{label}_{i + 1:02d}.png"
				pil.convert("RGB").save(out, format="PNG")
				created.append(out)
			finally:
				page.close()
	finally:
		pdf.close()

	# とりあえず全ページ出力できたことを成功条件とする
	if created:
		return created
	return []


def main() -> None:
	root = pathlib.Path(__file__).resolve().parents[1]
	input_dir = root / "diagrams"
	out_dir = root / "diagrams" / "00-exported" / "common"

	pdf_paths = sorted(input_dir.glob("*.pdf"))
	if not pdf_paths:
		print("[ERROR] diagrams/ 配下にPDFが見つかりませんでした。")
		return

	converter_scale = 2.0
	converter_quality = 90

	for pdf_path in pdf_paths:
		label = label_from_stem(pdf_path.stem)
		print(f"[INFO] convert: {pdf_path.name} -> {label}_<page>.png")

		created = export_pdf_to_png(
			pdf_path,
			out_dir,
			scale=converter_scale,
			quality=converter_quality,
		)
		if created:
			print(f"[OK] {len(created)} pages exported. deleting source pdf: {pdf_path.name}")
			pdf_path.unlink(missing_ok=True)
		else:
			print(f"[SKIP] no pages exported: {pdf_path.name}")


if __name__ == "__main__":
	main()

