"""
export_slides.py
NotebookLM 等で生成したスライドPDFを、全ページJPG画像として書き出す。

使い方:
  python scripts/export_slides.py --unit Unit02_JDBC --pdf slides-02.pdf
  python scripts/export_slides.py --unit Unit02_JDBC --pdf slides-02.pdf --scale 2.0 --quality 90

出力先:
  <unit>/slides/unit02-slide-p01.jpg
  <unit>/slides/unit02-slide-p02.jpg  ...
"""

import argparse
import pathlib
import re
import sys

import pypdfium2 as pdfium


def _unit_prefix(unit_dir: str) -> str:
    """Unit02_JDBC -> 'unit02' のようにプレフィックスを生成する。"""
    match = re.match(r"Unit(\d+)", unit_dir, re.IGNORECASE)
    if match:
        return f"unit{match.group(1).zfill(2)}"
    return unit_dir.lower()


def export_slides(
    unit_dir: pathlib.Path,
    pdf_path: pathlib.Path,
    scale: float = 2.0,
    quality: int = 90,
) -> list[pathlib.Path]:
    output_dir = unit_dir / "slides"
    output_dir.mkdir(parents=True, exist_ok=True)

    prefix = _unit_prefix(unit_dir.name)

    try:
        pdf = pdfium.PdfDocument(str(pdf_path))
    except Exception as exc:
        print(f"[ERROR] PDFを開けませんでした: {pdf_path} — {exc}", file=sys.stderr)
        sys.exit(1)

    created: list[pathlib.Path] = []
    page_count = len(pdf)
    print(f"[INFO] {page_count} ページを処理します: {pdf_path.name}")

    for i in range(page_count):
        page = pdf.get_page(i)
        try:
            pil = page.render(scale=scale).to_pil()
            out = output_dir / f"{prefix}-slide-p{i + 1:02d}.jpg"
            pil.convert("RGB").save(out, format="JPEG", quality=quality)
            created.append(out)
            print(f"  → {out.name}")
        finally:
            page.close()

    pdf.close()
    print(f"[DONE] {len(created)} 枚を {output_dir} に保存しました。")
    return created


def main() -> None:
    parser = argparse.ArgumentParser(description="スライドPDFを全ページJPGに変換する")
    parser.add_argument("--unit", required=True, help="対象Unitディレクトリ名 (例: Unit02_JDBC)")
    parser.add_argument("--pdf", required=True, help="PDFファイル名 (例: slides-02.pdf)")
    parser.add_argument("--scale", type=float, default=2.0, help="レンダリング倍率 (デフォルト: 2.0)")
    parser.add_argument("--quality", type=int, default=90, help="JPEG品質 0-95 (デフォルト: 90)")
    args = parser.parse_args()

    root = pathlib.Path(__file__).resolve().parents[1]
    unit_dir = root / args.unit
    if not unit_dir.is_dir():
        print(f"[ERROR] Unitディレクトリが見つかりません: {unit_dir}", file=sys.stderr)
        sys.exit(1)

    pdf_path = unit_dir / args.pdf
    if not pdf_path.exists():
        print(f"[ERROR] PDFファイルが見つかりません: {pdf_path}", file=sys.stderr)
        sys.exit(1)

    export_slides(unit_dir, pdf_path, scale=args.scale, quality=args.quality)


if __name__ == "__main__":
    main()
