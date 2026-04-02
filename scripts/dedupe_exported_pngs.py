import hashlib
import pathlib
import shutil

from PIL import Image


def image_pixel_hash(path: pathlib.Path) -> str:
	with Image.open(path) as im:
		rgba = im.convert("RGBA")
		w, h = rgba.size
		data = rgba.tobytes()
	digest = hashlib.sha256()
	digest.update(f"{w}x{h}\n".encode("utf-8"))
	digest.update(data)
	return digest.hexdigest()


def main() -> None:
	root = pathlib.Path(__file__).resolve().parents[1]
	exported = root / "diagrams" / "00-exported"
	unit01_dir = exported / "unit01"
	old_dir = exported / "old"
	old_dir.mkdir(parents=True, exist_ok=True)

	paths: list[pathlib.Path] = []
	for p in exported.rglob("*.png"):
		# always keep unit01 images
		try:
			p.relative_to(unit01_dir)
			continue
		except ValueError:
			pass
		# don't re-process already moved files
		try:
			p.relative_to(old_dir)
			continue
		except ValueError:
			pass
		paths.append(p)

	by_hash: dict[str, list[pathlib.Path]] = {}
	failures: list[tuple[pathlib.Path, str]] = []
	for p in sorted(paths):
		try:
			key = image_pixel_hash(p)
		except Exception as e:
			failures.append((p, str(e)))
			continue
		by_hash.setdefault(key, []).append(p)

	dupe_groups = [g for g in by_hash.values() if len(g) > 1]
	moved: list[tuple[pathlib.Path, pathlib.Path]] = []
	for group in dupe_groups:
		# keep the shortest path as representative, move the rest
		group_sorted = sorted(group, key=lambda x: (len(str(x)), str(x)))
		keep = group_sorted[0]
		for p in group_sorted[1:]:
			rel = p.relative_to(exported)
			dest = old_dir / rel
			dest.parent.mkdir(parents=True, exist_ok=True)
			shutil.move(str(p), str(dest))
			moved.append((p, dest))

	print(f"scanned={len(paths)}")
	print(f"hashes={len(by_hash)}")
	print(f"dupe_groups={len(dupe_groups)}")
	print(f"moved={len(moved)}")
	if failures:
		print(f"failures={len(failures)}")
		for p, msg in failures[:20]:
			print("FAIL", str(p), msg)

	report = old_dir / "dedupe_report.txt"
	with report.open("w", encoding="utf-8") as f:
		f.write(f"scanned={len(paths)}\n")
		f.write(f"hashes={len(by_hash)}\n")
		f.write(f"dupe_groups={len(dupe_groups)}\n")
		f.write(f"moved={len(moved)}\n")
		f.write("\n")
		for src, dst in moved:
			f.write(f"MOVED\t{src}\t->\t{dst}\n")
		if failures:
			f.write("\n")
			for p, msg in failures:
				f.write(f"FAIL\t{p}\t{msg}\n")

	print(str(report))

if __name__ == "__main__":
	main()

