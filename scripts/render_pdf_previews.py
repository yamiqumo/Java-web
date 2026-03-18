import pathlib

import pypdfium2 as pdfium


def render_previews(
	input_dir: pathlib.Path,
	output_dir: pathlib.Path,
	candidates: list[str],
	pages_1based: list[int],
	scale: float,
) -> list[pathlib.Path]:
	output_dir.mkdir(parents=True, exist_ok=True)

	created: list[pathlib.Path] = []
	for name in candidates:
		pdf_path = input_dir / name
		if not pdf_path.exists():
			continue

		try:
			pdf = pdfium.PdfDocument(str(pdf_path))
		except Exception as e:
			print("OPEN_FAIL", pdf_path.name, e)
			continue

		page_count = len(pdf)
		for p in pages_1based:
			if p < 1 or p > page_count:
				continue
			page = pdf.get_page(p - 1)
			try:
				pil = page.render(scale=scale).to_pil()
				out = output_dir / f"preview__{pdf_path.stem}__p{p}.png"
				pil.save(out)
				created.append(out)
			finally:
				page.close()
		pdf.close()
	return created


def main() -> None:
	root = pathlib.Path(__file__).resolve().parents[1]
	input_dir = root / "diagrams"
	output_dir = input_dir / "exported"

	candidates = [p.name for p in sorted(input_dir.glob("*.pdf"))]

	created = render_previews(
		input_dir=input_dir,
		output_dir=output_dir,
		candidates=candidates,
		pages_1based=[1, 2],
		scale=1.6,
	)
	print(f"created={len(created)}")
	print(str(output_dir))


if __name__ == "__main__":
	main()

