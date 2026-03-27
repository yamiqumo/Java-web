"""
embed_slide_links.py
Unit配下の slides/ ディレクトリにあるJPGから、GitHub rawリンクの <img> ブロックを生成する。

使い方:
  python scripts/embed_slide_links.py --unit Unit02_JDBC
  python scripts/embed_slide_links.py --unit Unit02_JDBC --branch main --out result.html

出力:
  標準出力（または --out で指定したファイル）にHTMLスニペットを書き出す。
  そのまま Html-rv-XX.html の任意の位置に貼り付けられる形式。

GitHub rawリンク形式:
  https://raw.githubusercontent.com/yamiqumo/Java-web/master/Unit02_JDBC/slides/unit02-slide-p01.jpg
"""

import argparse
import pathlib
import sys

GITHUB_OWNER = "yamiqumo"
GITHUB_REPO = "Java-web"
DEFAULT_BRANCH = "master"


def build_img_block(raw_url: str, alt: str) -> str:
    return (
        f'<div style="margin:1.5em 0;">\n'
        f'  <img src="{raw_url}"\n'
        f'       alt="{alt}"\n'
        f'       style="max-width:100%;border:1px solid #ccc;border-radius:6px;">\n'
        f'</div>'
    )


def generate_html(unit_dir: pathlib.Path, branch: str) -> str:
    slides_dir = unit_dir / "slides"
    if not slides_dir.is_dir():
        print(f"[ERROR] slides/ ディレクトリが見つかりません: {slides_dir}", file=sys.stderr)
        sys.exit(1)

    jpg_files = sorted(slides_dir.glob("*.jpg"))
    if not jpg_files:
        print(f"[WARN] JPGファイルが見つかりません: {slides_dir}", file=sys.stderr)
        return ""

    root = pathlib.Path(__file__).resolve().parents[1]
    blocks: list[str] = []

    for jpg in jpg_files:
        rel = jpg.relative_to(root).as_posix()
        raw_url = f"https://raw.githubusercontent.com/{GITHUB_OWNER}/{GITHUB_REPO}/{branch}/{rel}"
        alt = jpg.stem.replace("-", " ")
        blocks.append(build_img_block(raw_url, alt))

    print(f"[INFO] {len(blocks)} 枚分の <img> ブロックを生成しました。")
    return "\n\n".join(blocks)


def main() -> None:
    parser = argparse.ArgumentParser(description="slides/ のJPGからGitHub raw <img> ブロックを生成する")
    parser.add_argument("--unit", required=True, help="対象Unitディレクトリ名 (例: Unit02_JDBC)")
    parser.add_argument("--branch", default=DEFAULT_BRANCH, help=f"GitHubブランチ名 (デフォルト: {DEFAULT_BRANCH})")
    parser.add_argument("--out", default=None, help="出力先ファイルパス (省略時は標準出力)")
    args = parser.parse_args()

    root = pathlib.Path(__file__).resolve().parents[1]
    unit_dir = root / args.unit
    if not unit_dir.is_dir():
        print(f"[ERROR] Unitディレクトリが見つかりません: {unit_dir}", file=sys.stderr)
        sys.exit(1)

    html = generate_html(unit_dir, args.branch)

    if args.out:
        out_path = pathlib.Path(args.out)
        out_path.write_text(html, encoding="utf-8")
        print(f"[DONE] {out_path} に保存しました。")
    else:
        print("\n" + html)


if __name__ == "__main__":
    main()
